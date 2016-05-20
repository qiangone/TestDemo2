DROP DATABASE IF EXISTS  weidian;
CREATE DATABASE weidian character set utf8;
USE weidian;

CREATE TABLE address (
  id int(11) NOT NULL AUTO_INCREMENT,
  userid int(11) NOT NULL,
  is_default_address tinyint(4) DEFAULT '0',
  province varchar(20) NOT NULL,
  city varchar(20) DEFAULT NULL,
  district varchar(20) DEFAULT NULL,
  address_detail varchar(255) NOT NULL,
  contact_name varchar(10) NOT NULL,
  contact_number varchar(20) NOT NULL,
  zipcode varchar(20) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE appraisal (
  id int(11) NOT NULL AUTO_INCREMENT,
  order_detail_id int(11) NOT NULL,
  level int(11) NOT NULL COMMENT '1 = “好评”；\n0 = “中评”；\n-1 = “差评”',
  contents text NOT NULL,
  user_id int(11) NOT NULL,
  goods_id int(11) NOT NULL,
  create_time datetime NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE area (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(24) DEFAULT NULL COMMENT '名称',
  parent_id bigint(20) DEFAULT NULL COMMENT '父类id',
  lft bigint(20) DEFAULT NULL,
  rgt bigint(20) DEFAULT NULL,
  leaf tinyint(4) DEFAULT NULL COMMENT '是否有叶子节点',
  status tinyint(4) DEFAULT NULL COMMENT '状态',
  spell varchar(24) DEFAULT NULL COMMENT '拼音',
  full_path varchar(64) DEFAULT NULL COMMENT '全路径',
  icon varchar(24) DEFAULT NULL COMMENT '位置',
  des varchar(1024) DEFAULT NULL COMMENT '简介',
  position int(11) DEFAULT NULL COMMENT '位置',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='地区表';

CREATE TABLE cart (
  id int(11) NOT NULL AUTO_INCREMENT,
  userid int(11) NOT NULL,
  goods_id int(11) NOT NULL,
  stockprice_id int(11) DEFAULT NULL,
  quantity int(11) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE category (
  id int(11) NOT NULL AUTO_INCREMENT,
  shop_id int(11) DEFAULT NULL,
  name varchar(100) DEFAULT NULL,
  parent_id int(11) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE device (
  id bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  user_id bigint(11) NOT NULL,
  name varchar(32) DEFAULT NULL,
  os_version varchar(32) DEFAULT NULL,
  device_type tinyint(4) NOT NULL,
  token varchar(512) NOT NULL DEFAULT '',
  status tinyint(4) NOT NULL,
  update_time timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  channel_type int(11) DEFAULT NULL,
  app_id varchar(50) NOT NULL DEFAULT '',
  mp_key varchar(30) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE express_company (
  id int(11) NOT NULL AUTO_INCREMENT,
  abbrev varchar(50) DEFAULT NULL,
  name varchar(25) NOT NULL,
  logo varchar(45) DEFAULT NULL,
  url text NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY name_UNIQUE (name)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE goods (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(100) NOT NULL,
  shop_id int(11) NOT NULL,
  spec_name_first varchar(20) DEFAULT NULL,
  spec_name_second varchar(20) DEFAULT NULL,
  spec_name_third varchar(20) DEFAULT NULL,
  price float DEFAULT NULL,
  quantity int(11) DEFAULT '0',
  images text DEFAULT NULL,
  description text,
  excellent_num int(11) DEFAULT '0',
  medium_num int(11) DEFAULT '0',
  bad_num int(11) DEFAULT '0',
  is_on_sale tinyint(4) DEFAULT '0',
  create_time datetime DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE goods_category (
  id int(11) NOT NULL AUTO_INCREMENT,
  goods_id int(11) NOT NULL,
  category_id int(11) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE goods_stockprice (
  id int(11) NOT NULL AUTO_INCREMENT,
  goodsId int(11) DEFAULT NULL,
  specValueFirst varchar(20) DEFAULT NULL,
  specValueSecond varchar(20) DEFAULT NULL,
  specValueThird varchar(20) DEFAULT NULL,
  quantity int(11) NOT NULL,
  price float NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE oauth_access_token (
  token_id varchar(256) DEFAULT NULL,
  token blob,
  authentication_id varchar(256) DEFAULT NULL,
  user_name varchar(256) DEFAULT NULL,
  client_id varchar(256) DEFAULT NULL,
  authentication blob,
  refresh_token varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE oauth_client_details (
  client_id varchar(255) NOT NULL,
  resource_ids varchar(256) DEFAULT NULL,
  client_secret varchar(256) DEFAULT NULL,
  scope varchar(256) DEFAULT NULL,
  authorized_grant_types varchar(256) DEFAULT NULL,
  web_server_redirect_uri varchar(256) DEFAULT NULL,
  authorities varchar(256) DEFAULT NULL,
  access_token_validity int(11) DEFAULT NULL,
  refresh_token_validity int(11) DEFAULT NULL,
  additional_information varchar(4096) DEFAULT NULL,
  create_time datetime DEFAULT NULL,
  archived tinyint(1) DEFAULT '0',
  trusted tinyint(1) DEFAULT '0',
  PRIMARY KEY (client_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE oauth_client_token (
  token_id varchar(256) DEFAULT NULL,
  token blob,
  authentication_id varchar(256) DEFAULT NULL,
  user_name varchar(256) DEFAULT NULL,
  client_id varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE oauth_code (
  code varchar(256) DEFAULT NULL,
  authentication blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE oauth_refresh_token (
  token_id varchar(256) DEFAULT NULL,
  token blob,
  authentication blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE order_address (
  id int(11) NOT NULL AUTO_INCREMENT,
  order_master_id int(11) NOT NULL,
  province varchar(20) NOT NULL,
  city varchar(20) DEFAULT NULL,
  district varchar(20) DEFAULT NULL,
  address_detail varchar(255) NOT NULL,
  contact_name varchar(10) NOT NULL,
  contact_number varchar(20) NOT NULL,
  zipcode varchar(20) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE order_after_sale (
  id int(11) NOT NULL AUTO_INCREMENT,
  order_detail_id int(11) NOT NULL,
  order_master_id int(11) NOT NULL,
  type tinyint(4) NOT NULL DEFAULT '1' COMMENT '1：退款 2： 退货',
  status tinyint(4) NOT NULL DEFAULT '1' COMMENT '1 = 买家申请退款\n2 = 买家申请退货\n3 = 买家退货中\n4 = 买家确认收款\n5 = 退款完成\n默认1',
  is_received tinyint(4) DEFAULT '0' COMMENT '0:没收到 1：收到',
  reason varchar(30) DEFAULT NULL,
  detail text,
  money float DEFAULT NULL,
  attach varchar(1024) DEFAULT NULL,
  apply_time datetime NOT NULL,
  handle_result tinyint(4) DEFAULT NULL COMMENT '0：同意 1：拒绝',
  handle_reason text,
  handle_time datetime DEFAULT NULL,
  express_number varchar(50) DEFAULT NULL,
  express_company varchar(50) DEFAULT NULL,
  express_time datetime DEFAULT NULL,
  refund_time datetime DEFAULT NULL,
  refund_certificate varchar(50) DEFAULT NULL,
  appeal_title varchar(30) DEFAULT NULL,
  appeal_content text,
  appeal_time datetime DEFAULT NULL,
  finish_time datetime DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE order_detail (
  id int(11) NOT NULL AUTO_INCREMENT,
  order_master_id int(11) NOT NULL,
  price float NOT NULL,
  quantity int(11) NOT NULL,
  goods_id int(11) NOT NULL,
  goods_name varchar(50) NOT NULL,
  images text DEFAULT NULL,
  description text,
  stock_price_id int(11) DEFAULT NULL,
  spec_value_first varchar(50) DEFAULT NULL,
  spec_value_second varchar(50) DEFAULT NULL,
  spec_value_third varchar(50) DEFAULT NULL
  PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE order_master (
  id int(11) NOT NULL AUTO_INCREMENT,
  order_code varchar(50) DEFAULT NULL,
  user_id int(11) DEFAULT NULL,
  amount float DEFAULT NULL COMMENT '订单总额',
  status tinyint(4) NOT NULL,
  comments varchar(200) DEFAULT NULL,
  create_time datetime NOT NULL,
  pay_time datetime DEFAULT NULL COMMENT '订单支付时间',
  delivery_time datetime DEFAULT NULL COMMENT '订单发货时间',
  pay_transaction_no varchar(50) DEFAULT NULL COMMENT '（支付宝）交易号',
  express_number varchar(50) DEFAULT NULL COMMENT '快递订单号',
  express_company varchar(100) DEFAULT NULL,
  transport_expense float DEFAULT '0' COMMENT '运费',
  deal_time datetime DEFAULT NULL,
  shop_id int(11) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE reject_reason (
  type int(11) DEFAULT NULL COMMENT '0： 退款 1：退货',
  reason varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE seller (
  id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(45) DEFAULT NULL,
  password varchar(20) DEFAULT NULL,
  phonenumber varchar(45) DEFAULT NULL,
  email varchar(100) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY username_UNIQUE (username)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE shop (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(45) DEFAULT NULL,
  description text,
  sellerid int(11) DEFAULT NULL,
  token varchar(100) DEFAULT NULL,
  transportexpense float DEFAULT NULL,
  logo varchar(1024) DEFAULT NULL,
  backgroundimage varchar(1024) DEFAULT NULL,
  address varchar(100) DEFAULT NULL,
  postcode varchar(20) DEFAULT NULL,
  contact varchar(10) DEFAULT NULL,
  telephone varchar(20) DEFAULT NULL,
  province varchar(20) DEFAULT NULL,
  city varchar(20) DEFAULT NULL,
  region varchar(20) DEFAULT NULL,
  wechatappkey varchar(50) DEFAULT NULL,
  wechatappsecret varchar(100) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE sms (
  id int(11) NOT NULL AUTO_INCREMENT,
  phonenumber varchar(45) DEFAULT NULL,
  securitycode varchar(45) DEFAULT NULL,
  createdtime datetime DEFAULT NULL,
  type int(11) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE user (
  id int(11) NOT NULL AUTO_INCREMENT,
  openid varchar(20) NOT NULL,
  shopid int(11) NOT NULL,
  registertime datetime DEFAULT NULL,
  nickname varchar(20) DEFAULT NULL,
  icon varchar(100) DEFAULT NULL,
  lastlogintime datetime DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;




