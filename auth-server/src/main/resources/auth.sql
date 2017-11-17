CREATE DATABASE auth_test;

USE auth_test;

CREATE TABLE user
(
  username VARCHAR(256) NULL,
  password VARCHAR(256) NULL,
  role     VARCHAR(256) NULL,
  enabled  TINYINT(1)   NULL,
  id       BIGINT       NOT NULL
);

CREATE TABLE oauth_refresh_token
(
  token_id       VARCHAR(256) NULL,
  token          BLOB         NULL,
  authentication BLOB         NULL
);

CREATE TABLE oauth_code
(
  code           VARCHAR(256) NULL,
  authentication BLOB         NULL
);

CREATE TABLE oauth_client_token
(
  token_id          VARCHAR(256) NULL,
  token             BLOB         NULL,
  authentication_id VARCHAR(256) NULL,
  user_name         VARCHAR(256) NULL,
  client_id         VARCHAR(256) NULL
);

CREATE TABLE oauth_client_details
(
  client_id               VARCHAR(256)  NOT NULL
    PRIMARY KEY,
  resource_ids            VARCHAR(256)  NULL,
  client_secret           VARCHAR(256)  NULL,
  scope                   VARCHAR(256)  NULL,
  authorized_grant_types  VARCHAR(256)  NULL,
  web_server_redirect_uri VARCHAR(256)  NULL,
  authorities             VARCHAR(256)  NULL,
  access_token_validity   INT           NULL,
  refresh_token_validity  INT           NULL,
  additional_information  VARCHAR(4096) NULL,
  autoapprove             VARCHAR(256)  NULL
);

CREATE TABLE oauth_access_token
(
  token_id          VARCHAR(256) NULL,
  token             BLOB         NULL,
  authentication_id VARCHAR(256) NULL,
  user_name         VARCHAR(256) NULL,
  client_id         VARCHAR(256) NULL,
  authentication    BLOB         NULL,
  refresh_token     VARCHAR(256) NULL
);

CREATE TABLE authorities
(
  username  VARCHAR(256) NULL,
  authority VARCHAR(256) NULL
);

