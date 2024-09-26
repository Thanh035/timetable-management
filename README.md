<p align="center">
  <br />
  <a href="https://sonic.tech">
    <img src="./static/images/logo/logo-sonic.svg" height="60px">
  </a>
</p>

<p align="center">
  <a href="https://sonic.tech">Sonic</a> Website built using <a href="https://spring.io/projects/spring-boot">Spring Boot</a>, a modern static website generator.
</p>
<br />

### Installation

```
npm install
```

### Local Development

```
npm start
```

This command starts a local development server and opens up a browser window. Most changes are reflected live without having to restart the server.

### Build

```
npm run build
```

This command generates static content into the `build` directory and can be served using any static content hosting service.

### Deployment

Using SSH:

```
USE_SSH=true npm run deploy
```

Not using SSH:

```
GIT_USER=<Your GitHub username> npm run deploy
```

If you are using GitHub pages for hosting, this command is a convenient way to build the website and push to the `gh-pages` branch.
