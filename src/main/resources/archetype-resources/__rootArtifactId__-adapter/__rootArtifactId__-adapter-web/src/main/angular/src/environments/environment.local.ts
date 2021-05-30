export const environment = {
  production: false,
  config: {
    aplicacion: '${rootArtifactId}',
    oauth: {
      issuer: '@server.security.oidc.issuer@',
      clientId: '@application.security.oidc.client-id@',
      clientSecret: '@application.security.oidc.client-secret@',
      scope: 'email profile',
      redirectUri: '@server.host.protocol@://@server.host.name@@application.context-root@/ui/index.html',
      silentRefreshUri: '@server.host.protocol@://@server.host.name@@application.context-root@/ui/assets/silent-refresh.html'
    },
    menu: {
      url: '@server.host.protocol@://@server.host.name@@application.context-root@'
    }
  }
};
