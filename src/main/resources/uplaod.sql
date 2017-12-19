create  table upload_file(
    id int AUTO_INCREMENT primary key not null comment '',
    user_code varchar(256)   not null comment '用户唯一标识',
    user_type varchar(256)  not null comment '用户类型',
    user_role varchar(256)  not null comment '用户角色',
    url varchar(256)  not null comment '地址 ',
    bucket_name varchar(256)  not null comment '' ,
    file_length varchar(256) not null comment '文件大小',
    file_name varchar(256)   not null comment '文件名',
    file_upload_name varchar(256)   not null comment '文件名',
    file_suffix varchar(32)  not null comment '后缀',
    etag   varchar(32)  not null comment 'etag',
    status int not null ,
    created_time long not null comment '插入时间'
);