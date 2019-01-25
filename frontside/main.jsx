var React = require('react');
var ReactDOM = require('react-dom');
var ReactRouterDom = require('React-Router-Dom');
var ws = new WebSocket('ws://localhost:8080/casino/websocket');


      var HashRouter = require('React-Router-Dom').HashRouter;
      var Link = require('React-Router-Dom').Link;
      var Route = require('React-Router-Dom').Route;
      var Switch = require('React-Router-Dom').Switch;
	  
	  
      var FormGroup = require('react-reactive-form').FormGroup;
      var ControlLabel = require('react-reactive-form').ControlLabel;
      var FormControl = require('react-reactive-form').FormControl;
	  
	  var uname="";
	  var pword ="";
	  
	  	  var retOptions="";
		  
		  
	  	  var obj0 = {
    id: "5",
    lobbyname: "Highrollers",
    gametype: "Texas Holdem",
    playersCurr: "2",
    playersMax: "6"
};
	  var obj1 = {
    id: "10",
    lobbyname: "Ezi game free money",
    gametype: "Texas Holdem",
    playersCurr: "1",
    playersMax: "4"
};	  var obj2 = {
    id: "3",
    lobbyname: "testlobby",
    gametype: "Texas Holdem",
    playersCurr: "4",
    playersMax: "4"
};
	  var cars = [obj0, obj1, obj2];
	  
	  
ws.onopen = () => {
  // connection opened
 // ws.send('something'); // send a message
};

ws.onmessage = (e) => {
  // a message was received
  console.log(e);
};

ws.onerror = (e) => {
  // an error occurred
  console.log(e);
};

ws.onclose = (e) => {
  // connection closed
  console.log(e.code, e.reason);
};



 class LoginForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {valueName: '', valuePass: ''};

    this.handleChangeName = this.handleChangeName.bind(this);
    this.handleChangePass = this.handleChangePass.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChangeName(event) {
	  uname = event.target.value;
    this.setState({valueName: event.target.value});
  }
  handleChangePass(event) {
    this.setState({valuePass: event.target.value});
  }
  
  
  

  handleSubmit(event) {
	  
	  ws.send("{ \"request\" : \"login\", \"username\" : \""+this.state.valueName+"\", \"password\": \""+this.state.valuePass+"\" }");
    alert('A name was submitted: ' + this.state.valueName + ', Password is ' +this.state.valuePass);
    event.preventDefault();
      this.props.history.push('/lobby');
  }

  render() {
    return (
	
	<div id="login">
	<div >
      <form id="loginForm" onSubmit={this.handleSubmit}>
	  
	  
	<div id="loginDiv">
	  <Link to="/lobby"> <h2> Log in </h2> </Link>
        <label>
          <input type="text" value={this.state.valueName} onChange={this.handleChangeName} placeholder="Username"/>
        </label>
		<br />
		<br />
        <label>
          <input type="password" value={this.state.valuePass} onChange={this.handleChangePass} placeholder="Password"/>
        </label>
		<br />
        <input type="submit" value="Log in"/>
	
		
	</div>
		
		
      </form>
	  </div>
	  </div>
    );
  }
}













 class TestComp extends React.Component {
  constructor(props) {
    super(props);
	
    var test;

    this.onDropdownSelected = this.onDropdownSelected.bind(this);
    this.joinLobby = this.joinLobby.bind(this);
  }
  
  
  
    joinLobby(event) {
	//ws.send("{ \"request\" : \"join\", \"join\" : "+cars[test].id+" }");
	console.log("{ \"request\" : \"join\", \"join\" : "+cars[test].id+" }");
		//alert(cars[test].id);
      this.props.history.push('/queue');
  }
  
    onDropdownSelected(event) {
   var selectedValue = event.target.options[event.target.selectedIndex].value;
   test = selectedValue;
    console.log(selectedValue);
  }
  
  
  
  
  
  createSelectItems() {
	
	cars = [];
	retOptions = "<option disabled selected value> -- Texas Holdem -- </option>";
	  var cars = [obj0, obj1, obj2];

	  Object.keys(cars).forEach(function(k){
   // console.log(k + ' - ' + cars[k]);
	retOptions=retOptions+ "<option value=\""+k+"\">"+cars[k].lobbyname+" "+cars[k].gametype+" "+cars[k].playersCurr+"/"+cars[k].playersMax+"</option> \n";
});
   // console.log(retOptions);
    return retOptions;
  }

  
  render() {
	  
    return (
<div id="lobbyDiv">
<div id="lobby">

      <form id="asdf" onSubmit={this.joinLobby}>
 <select id="userID" size="35" onChange={this.onDropdownSelected}>
    <optgroup label="Active Games" dangerouslySetInnerHTML={{__html: this.createSelectItems() }}></optgroup>

</select>
<br/>
<input type="submit" value="Join Lobby"/>
<button type="button" >New Lobby</button>
<button type="button" >Refresh</button>

<button type="button"><Link to="/Students">Students</Link></button>
      </form>
	  </div>
	  </div>


    );
  }
}








class Queue extends React.Component {
  constructor(props) {
    super(props);
	
  }
 
  
 

  
  render() {
	  
    return (
<div id="queue">

	<h1>Waiting for game to start</h1>
	  </div>


    );
  }
}


 class NoMatch extends React.Component {
  constructor(props) {
    super(props);
    this.state = {value: ''};

  }


  render() {
    return (
	
	<div>
	<h1>nup</h1>
	  </div>
    );
  }
}


ReactDOM.render(
	<HashRouter>
	<Switch>
	
	  
	  <Route path="/lobby" component={TestComp}/>
	  <Route path="/queue" component={Queue}/>
<Route path="/login" component={LoginForm}/>
<Route component={NoMatch}/>
	  
	  </Switch>
	</HashRouter>,
  document.getElementById('app')
);