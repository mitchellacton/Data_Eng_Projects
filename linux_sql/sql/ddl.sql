CREATE TABLE IF NOT EXISTS host_info
(
    id SERIAL           PRIMARY KEY NOT NULL,
    hostname            VARCHAR UNIQUE NOT NULL,
    cpu_number          INT NOT NULL,
    cpu_architecture    VARCHAR NOT NULL,
    cpu_model           VARCHAR NOT NULL,
    cpu_mhz             FLOAT NOT NULL,
    l2_cache            INT NOT NULL,
    total_mem           INT NOT NULL,
    "timestamp"         TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS host_usage
(
    timestamp     TIMESTAMP NOT NULL,
    host_id         INTEGER NOT NULL,
    memory_free     INT NOT NULL,
    cpu_idle        FLOAT NOT NULL,
    cpu_kernel      FLOAT NOT NULL,
    disk_io         INT NOT NULL,
    disk_available  INT NOT NULL,
    CONSTRAINT PK_host_usage PRIMARY KEY(timestamp, host_id),
    CONSTRAINT fk_usage FOREIGN KEY(host_id)
        REFERENCES host_info(id)
    )