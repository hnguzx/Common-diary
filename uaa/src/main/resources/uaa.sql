-- used in tests that use HSQL
create table oauth_client_details
(
    client_id               VARCHAR(128) PRIMARY KEY,
    resource_ids            VARCHAR(128),
    client_secret           VARCHAR(128),
    scope                   VARCHAR(128),
    authorized_grant_types  VARCHAR(128),
    web_server_redirect_uri VARCHAR(128),
    authorities             VARCHAR(128),
    access_token_validity   INTEGER,
    refresh_token_validity  INTEGER,
    additional_information  VARCHAR(4096),
    autoapprove             VARCHAR(128)
);
create table oauth_client_token
(
    token_id          VARCHAR(128),
    token             BLOB,
    authentication_id VARCHAR(128) PRIMARY KEY,
    user_name         VARCHAR(128),
    client_id         VARCHAR(128)
);
create table oauth_access_token
(
    token_id          VARCHAR(128),
    token             BLOB,
    authentication_id VARCHAR(128) PRIMARY KEY,
    user_name         VARCHAR(128),
    client_id         VARCHAR(128),
    authentication    BLOB,
    refresh_token     VARCHAR(128)
);
create table oauth_refresh_token
(
    token_id       VARCHAR(128),
    token          BLOB,
    authentication BLOB
);
create table oauth_code
(
    code           VARCHAR(128),
    authentication BLOB
);
create table oauth_approvals
(
    userId         VARCHAR(128),
    clientId       VARCHAR(128),
    scope          VARCHAR(128),
    status         VARCHAR(10),
    expiresAt      TIMESTAMP,
    lastModifiedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- customized oauth_client_details table
create table ClientDetails
(
    appId                  VARCHAR(128) PRIMARY KEY,
    resourceIds            VARCHAR(128),
    appSecret              VARCHAR(128),
    scope                  VARCHAR(128),
    grantTypes             VARCHAR(128),
    redirectUrl            VARCHAR(128),
    authorities            VARCHAR(128),
    access_token_validity  INTEGER,
    refresh_token_validity INTEGER,
    additionalInformation  VARCHAR(4096),
    autoApproveScopes      VARCHAR(128)
);

INSERT INTO oauth_client_details(client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove)
VALUES ('user', 'resource1', '$2a$10$9s.TeXckKO9URjDn3mc/9.7zUW4D4qfkkEaxPHfyCoQCsNLwMw4P.', 'scope1,scope2',
        'authorization_code,password,client_credentials,implicit,refresh_token', 'http://www.baidu.com', NULL, 300, 1500, NULL, 'false');