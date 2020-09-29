create table users (
    id      bigserial primary key,
    name    varchar(50),
    email    varchar(50),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    deleted_at timestamp
);

create table books (
    id      bigserial primary key,
    title   varchar(50),
    qty     int default 0,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    deleted_at timestamp
);

create table transaction_headers (
    id      bigserial primary key,
    user_id int references users (id),
    transaction_date timestamp,
    deadline_date timestamp,
    return_date timestamp,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    deleted_at timestamp
);

create table transaction_details (
    id bigserial primary key,
    header_id int references transaction_headers (id),
    book_id int references books (id),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    deleted_at timestamp
);