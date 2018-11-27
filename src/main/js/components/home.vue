<template>
    <div class="home">
        <section class="hero is-medium is-primary is-bold">
            <div class="hero-body">
                <div class="container">
                    <h1 class="title">{{ heading }}</h1>
                    <div class="is-two-thirds column is-paddingless">
                        <h2 class="subtitle is-4">{{ subheading }}</h2>
                    </div>
                    <a class="button is-large is-primary" id="learn">Games</a>
                </div>
            </div>
        </section>

        <section class="section">
            <div class="container">
                <div class="columns pd is-desktop">
                    <div class="column is-1 has-text-centered">
                        <i class="fa fa-cog is-primary"></i>
                    </div>
                    <div class="column is-one-third-desktop">
                        <p class="title"><strong>Easy to sign up</strong></p>
                    </div>
                    <div class="column">
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam.</p>
                    </div>
                </div>
            </div>

            <div class="columns pd">
                <div class="column">
                    <div class="card">
                        <div class="card-content">
                            <p class="title">"I won 5000$ the first time"</p>
                            <p class="subtitle">- Phil </p>
                        </div>
                    </div>
                </div>
                <div class="column">
                    <div class="card">
                        <div class="card-content">
                            <p class="title">"I'll be back !"</p>
                            <p class="subtitle">- you know who</p>
                        </div>
                    </div>
                </div>
                <div class="column">
                    <div class="card">
                        <div class="card-content">
                            <p class="title">"fast, easy transering all my big money!"</p>
                            <p class="subtitle">- Big K</p>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</template>
<script>
    export default {
        name: 'home',
        data () {
            return {
                heading: 'Welcome',
                subheading: 'Let the game begin'
            }
        }
    }
</script>