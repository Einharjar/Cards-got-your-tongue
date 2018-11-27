

<template>
    <div id="app">
        <nav class="navbar" role="navigation" aria-label="main navigation">
            <div class="navbar-brand">
                <a class="navbar-item" href="https://bulma.io">
                  <p>CGYT-logo</p>
                </a>

                <a role="button" class="navbar-burger burger" aria-label="menu" aria-expanded="false" data-target="navbarBasicExample">
                    <span aria-hidden="true"></span>
                    <span aria-hidden="true"></span>
                    <span aria-hidden="true"></span>
                </a>
            </div>

            <div id="navbarBasicExample" class="navbar-menu">
                <div class="navbar-start">
                    <a class="navbar-item">
                        <router-link to="/" class="nav-item r-item">Home</router-link>
                    </a>


                    <a class="navbar-item">
                        <router-link to="/registerPage" class="nav-item r-item">Register</router-link>
                    </a>

                    <div class="navbar-item has-dropdown is-hoverable">
                        <a class="navbar-link">
                            More
                        </a>

                        <div class="navbar-dropdown">
                            <a class="navbar-item">
                                <router-link to="/homePage" class="nav-item r-item">GamePage(denna länk skall ej finnas sen)</router-link>
                            </a>
                            <a class="navbar-item">
                                bla
                            </a>
                            <a class="navbar-item">
                                bla
                            </a>
                            <hr class="navbar-divider">
                            <a class="navbar-item">
                                bla
                            </a>
                        </div>
                    </div>
                </div>

                <div class="navbar-end">
                    <div class="navbar-item">
                        <div class="buttons">
                            <a class="button is-primary">
                                <router-link to="/registerPage">Sign Up</router-link>

                            </a>
                            <a class="button is-light">
                               <router-link to="/loginPage">Log in</router-link>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </nav>
<!----HERE IS WHERE THE CONTENt IS SHOWN--->
        <router-view></router-view>

        <footer class="footer is-primary">
            <div class="container">
                <div class="columns">
                    <div class="column">
                        <p>Chat Got Your Tongue</p>
                    </div>
                    <div class="column has-text-right">
                        <a class="icon" href="#"><i class="fa fa-facebook"></i></a>
                        <a class="icon" href="#"><i class="fa fa-twitter"></i></a>
                    </div>
                </div>
            </div>
        </footer>
    </div>
</template>
<script>
    export default {
        name: 'app',
        data: function() {
            return {
                isActive: false
            }
        },
        methods: {
            toggleNav: function() {
                this.isActive = !this.isActive;
            }
        }
    }
</script>

