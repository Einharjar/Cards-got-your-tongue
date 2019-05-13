var React = require("react");
var ReactDOM = require("react-dom");
var ReactRouterDom = require("React-Router-Dom");
var ws = new WebSocket("ws://localhost:8080/casino/websocket");

var HashRouter = require("React-Router-Dom").HashRouter;
var Link = require("React-Router-Dom").Link;
var Route = require("React-Router-Dom").Route;
var Switch = require("React-Router-Dom").Switch;

var FormGroup = require("react-reactive-form").FormGroup;
var ControlLabel = require("react-reactive-form").ControlLabel;
var FormControl = require("react-reactive-form").FormControl;

let globalHistory = null;
var currentState = null;
var bananas = 0;
var lobby = def;
var inGame = false;

var def = {
  lobbyname: "Highrollers",
  host: "½!#¤%&/()=?`"
};
var uname = "`?=)(/&%¤#!½";

var retOptions = "";

var gameState = "";

var globalLobbies = [];

//  <SOURCE: https://stackoverflow.com/a/31849204>
function pollFunc(fn, timeout, interval) {
  var startTime = new Date().getTime();
  interval = interval || 1000;

  (function p() {
    fn();
    if (new Date().getTime() - startTime <= timeout) {
      setTimeout(p, interval);
    }
  })();
}
//</SOURCE>

ws.onopen = () => {
  console.log("Socket Open");
};

ws.onsend = () => {
  console.log("Socket Open");
};

ws.onmessage = e => {
  if (isJson(e.data)) {
    var incomingObj = JSON.parse(e.data);
    console.log("Incoming : ");
    console.log(incomingObj);

    if (incomingObj.success == true) {
      if (incomingObj.request == "login") {
        console.log(incomingObj.success);
        globalHistory.push("/lobby");
      } else if (incomingObj.request == "newlobby") {
        var obj0 = {
          id: incomingObj.id,
          lobbyname: incomingObj.name,
          host: incomingObj.host,
          gametype: "Texas Holdem",
          playersCurr: incomingObj.playerscurr,
          playersMax: incomingObj.playersmax
        };
        lobby = obj0;
        globalLobbies.push(obj0);
      } else if (incomingObj.request == "clearlobbies") {
        globalLobbies.splice(0, globalLobbies.length);
      } else if (incomingObj.request == "getlobbies") {
        var newgame = {
          id: incomingObj.id,
          lobbyname: incomingObj.name,
          host: incomingObj.host,
          gametype: "Texas Holdem",
          playersCurr: incomingObj.playerscurr,
          playersMax: incomingObj.playersmax
        };
        globalLobbies.push(newgame);
      } else if (incomingObj.request == "join") {
        var obj0 = {
          id: incomingObj.id,
          lobbyname: incomingObj.name,
          host: incomingObj.host,
          gametype: "Texas Holdem",
          playersCurr: incomingObj.playerscurr,
          playersMax: incomingObj.playersmax
        };
        lobby = obj0;
        globalLobbies.push(obj0);
        globalHistory.push("/queue");
      } else if (incomingObj.request == "start") {
        globalHistory.push("/holdem");
      } else if (incomingObj.request == "update") {
        if (incomingObj.game == "holdem") {
          if (gameState === "") {
            gameState = incomingObj;
          }

          if (incomingObj.hasOwnProperty("table")) {
            gameState.table = incomingObj.table;
          }
          if (incomingObj.hasOwnProperty("player")) {
            gameState.player = incomingObj.player;
          }
          if (incomingObj.hasOwnProperty("activePlayer")) {
            gameState.activePlayer = incomingObj.activePlayer;
          }
          if (incomingObj.hasOwnProperty("pot")) {
            gameState.pot = incomingObj.pot;
          }
          if (incomingObj.hasOwnProperty("table")) {
            gameState.table = incomingObj.table;
          }
          if (incomingObj.hasOwnProperty("winner")) {
            gameState.winner = incomingObj.winner;
          }
          if (incomingObj.hasOwnProperty("bananas")) {
            bananas = incomingObj.bananas;
          }
        }
      } else {
        console.log("Unknown WS transmit");
      }
    }
  } else {
    console.log(e.data);
  }
};

ws.onerror = e => {
  console.log(e);
};

ws.onclose = e => {
  console.log(e.code, e.reason);
  console.log("Socket Closed");
  globalHistory.push("/login");
};

class LoginForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = { valueName: "", valuePass: "" };

    this.handleChangeName = this.handleChangeName.bind(this);
    this.handleChangePass = this.handleChangePass.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);

    globalHistory = this.props.history;
    currentState = this.state;
  }

  handleChangeName(event) {
    uname = event.target.value;
    this.setState({ valueName: event.target.value });
  }
  handleChangePass(event) {
    this.setState({ valuePass: event.target.value });
  }

  handleSubmit(event) {
    var json = {
      request: "login",
      username: this.state.valueName,
      password: this.state.valuePass
    };
    ws.send(JSON.stringify(json));
  }

  render() {
    inGame = false;
    return (
      <div id="login">
        <div>
          <form id="loginForm" onSubmit={this.handleSubmit}>
            <div id="loginDiv">
              <h2> Log in </h2>
              <label>
                <input
                  type="text"
                  value={this.state.valueName}
                  onChange={this.handleChangeName}
                  placeholder="Username"
                />
              </label>
              <br />
              <br />
              <label>
                <input
                  type="password"
                  value={this.state.valuePass}
                  onChange={this.handleChangePass}
                  placeholder="Password"
                />
              </label>
              <br />
              <input type="submit" value="Log in" />
            </div>
          </form>
        </div>
      </div>
    );
  }
}

class LobbySelection extends React.Component {
  constructor(props) {
    super(props);
    this.state = { games: globalLobbies };

    var selectedValue = 0;
    this.onDropdownSelected = this.onDropdownSelected.bind(this);
    this.joinLobby = this.joinLobby.bind(this);
    this.refreshLobbies = this.refreshLobbies.bind(this);
    this.newLobby = this.newLobby.bind(this);
    this.createSelectItems = this.createSelectItems.bind(this);

    globalHistory = this.props.history;
    currentState = this.state;
  }

  refreshLobbies() {
    var json = {
      request: "string",
      string: "getlobbies"
    };
    ws.send(JSON.stringify(json));

    this.setState({ games: globalLobbies });
  }

  joinLobby(event) {
    this.refreshLobbies();

    var json = {
      request: "join",
      join: selectedValue
    };
    ws.send(JSON.stringify(json));
    console.log(globalLobbies[selectedValue]);
    lobby = globalLobbies[selectedValue];
  }

  onDropdownSelected(event) {
    selectedValue = event.target.options[event.target.selectedIndex].value;
    //console.log(event.target.options[event.target.selectedIndex]);
  }

  createSelectItems() {}

  newLobby() {
    this.refreshLobbies();
    var json = {
      request: "string",
      string: "newlobby"
    };
    ws.send(JSON.stringify(json));
  }

  render() {
    inGame = false;

    let rows = this.state.games.map(game => {
      return <LobbyRow key={game.id} data={game} />;
    });

    return (
      <div id="lobbyDiv">
        <div id="lobby">
          <form id="asdf" onSubmit={this.joinLobby}>
            <select id="userID" size="35" onChange={this.onDropdownSelected}>
              <optgroup label="Active Games" />
              <option disabled selected value>
                {" "}
                -- Texas Holdem --{" "}
              </option>
              {rows}
            </select>
            <br />
            <button type="button" onClick={this.joinLobby}>
              Join Lobby
            </button>
            <button type="button" onClick={this.newLobby}>
              New Lobby
            </button>
            <button type="button" onClick={this.refreshLobbies}>
              Refresh
            </button>
          </form>
        </div>
      </div>
    );
  }
}

class Queue extends React.Component {
  constructor(props) {
    super(props);
    this.startGame = this.startGame.bind(this);
    globalHistory = this.props.history;
    currentState = this.state;
  }

  startGame() {
    var json = {
      request: "string",
      string: "start"
    };

    ws.send(JSON.stringify(json));
  }

  render() {
    inGame = false;

    var json = {
      request: "string",
      string: "getlobbies"
    };
    ws.send(JSON.stringify(json));

    return (
      <div id="queue">
        // {lobby.playersCurr} : players in the lobby
        {(lobby.host == uname && (
          <div>
            <h1>host boi</h1>
            <button type="button" onClick={this.startGame}>
              Start game
            </button>
          </div>
        )) ||
          (lobby.host != uname && (
            <div>
              <h1>Waiting for game to start</h1>
            </div>
          ))}
      </div>
    );
  }
}

class HoldemComp extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      pot: 0,
      raiseVal: 0,
      table: [],
      thisPlayer: [],
      activePlayer: "",
      currentPlayer: [],
      winner: "",
      test: 0
    };
    var newpot = 0;

    this.handleChangePot = this.handleChangePot.bind(this);
    this.fold = this.fold.bind(this);
    this.call = this.call.bind(this);
    this.raise = this.raise.bind(this);
    this.changeTest = this.changeTest.bind(this);
    this.getCurrentTable = this.getCurrentTable.bind(this);
    globalHistory = this.props.history;
    currentState = this.state;

    pollFunc(this.changeTest, 60000000, 500);
  }

  changeTest() {
    console.log("asdf");
    if (gameState.hasOwnProperty("game")) {
      if (gameState.hasOwnProperty("pot")) {
        this.setState({ pot: gameState.pot });
      }
      if (gameState.hasOwnProperty("table")) {
        this.setState({ table: gameState.table });
      }
      if (gameState.hasOwnProperty("player")) {
        this.setState({ thisPlayer: gameState.player });
      }
      if (gameState.hasOwnProperty("activePlayer")) {
        this.setState({ activePlayer: gameState.activePlayer });
      }
      if (gameState.hasOwnProperty("winner")) {
        this.setState({ winner: gameState.winner });
      }
    }
  }

  handleChangePot(event) {
    if (/^[0-9\b]+$/.test(event.target.value)) {
      this.setState({ raiseVal: event.target.value });
    }
  }

  getCurrentTable() {
    return this.state.table;
  }

  fold() {
    var json = {
      request: "game",
      action: "fold"
    };
    ws.send(JSON.stringify(json));
  }
  call() {
    var json = {
      request: "game",
      action: "call"
    };
    ws.send(JSON.stringify(json));
  }
  raise() {
    var json = {
      request: "game",
      action: "raise",
      raise: this.state.raiseVal
    };
    ws.send(JSON.stringify(json));
    console.log(JSON.stringify(json));
  }

  render() {
    inGame = true;
    if (this.state.winner === "") {
      return (
        <div id="holdemBackground">
          <div id="holdemGame">
            <h1>Texas Holdem</h1>
            <h1>Player {this.state.activePlayer}'s turn</h1>
            <div id="holdemTable">
              <h1>{this.getCurrentTable()}</h1>
              <h1>Pot: {this.state.pot}</h1>
            </div>
            <h1> {this.state.thisPlayer} </h1>
            <h1> {uname} </h1>
            <h1> {bananas}</h1>
            <button
              type="button"
              disabled={this.state.activePlayer !== uname ? "disabled" : null}
              onClick={this.fold}
            >
              Fold
            </button>
            <button
              type="button"
              disabled={this.state.activePlayer !== uname ? "disabled" : null}
              onClick={this.call}
            >
              Call
            </button>
            <input
              type="number"
              disabled={this.state.activePlayer !== uname ? "disabled" : null}
              value={+this.state.raiseVal}
              onChange={this.handleChangePot}
              placeholder="Raise Amount"
            />
            <button
              type="button"
              disabled={this.state.activePlayer !== uname ? "disabled" : null}
              onClick={this.raise}
            >
              Raise
            </button>
          </div>
        </div>
      );
    } else {
      return (
        <div id="holdemBackground">
          <div id="holdemGame">
            <h1>The winner is: {this.state.winner} </h1>
          </div>
        </div>
      );
    }
  }
}

class NoMatch extends React.Component {
  constructor(props) {
    super(props);
    this.state = { value: "" };
  }

  render() {
    return (
      <div>
        <h1>404 - Page not found</h1>
      </div>
    );
  }
}
function isJson(str) {
  try {
    JSON.parse(str);
  } catch (e) {
    return false;
  }
  return true;
}

const LobbyRow = props => {
  return (
    <option value={props.data.id}>
      {props.data.lobbyname} {props.data.gametype} {props.data.playersCurr} /{" "}
      {props.data.playersMax}
    </option>
  );
};

ReactDOM.render(
	<HashRouter>
	<Switch>
	
	  
	  <Route path="/lobby" component={LobbySelection}/>
	  <Route path="/queue" component={Queue}/>
	  <Route path="/holdem" component={HoldemComp}/>
<Route path="/login" component={LoginForm}/>
<Route path="*"  status={404}/>

	  
	  </Switch>
	</HashRouter>,
  document.getElementById('app')
);
