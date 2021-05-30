export const environment = {
  production: false,
  config: {
    aplicacion: '${rootArtifactId}',
    oauth: {
      issuer: 'https://sso.lujociclas.com/auth/realms/Lujociclas-dev',
      clientId: '${rootArtifactId}',
      clientSecret: 'secret',
      scope: 'email profile',
      redirectUri: `${window.location.origin}/index.html`,
      silentRefreshUri: `${window.location.origin}/assets/silent-refresh.html`
    },
    menu: {
      url: `http://localhost:8342/wap/${rootArtifactId}`
    }
  }
};
