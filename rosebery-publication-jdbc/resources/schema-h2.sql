CREATE TABLE node (
    id IDENTITY PRIMARY KEY,
    node_id VARCHAR(50),
    node_purpose VARCHAR(50),
    CONSTRAINT node_unique_ct UNIQUE (node_id, node_purpose)
);

CREATE TABLE runtime_performance (
    id IDENTITY PRIMARY KEY,
    node_id BIGINT,
    seq VARCHAR(100),
    starttime BIGINT,
    endtime BIGINT,
    duration BIGINT,
    result VARCHAR(15),
    CONSTRAINT rtp_unique UNIQUE (node_id, starttime, endtime),
    CONSTRAINT rtp_node_fk FOREIGN KEY (node_id) REFERENCES node(id)
);

CREATE TABLE execution_profile (
    rtp_id BIGINT PRIMARY KEY,
    CONSTRAINT rtp_ep_fk FOREIGN KEY (rtp_id) REFERENCES runtime_performance(id)
);

CREATE TABLE resource_snapshot (
    ep_id BIGINT,
    nanotime BIGINT,
    system_cpu_load DOUBLE,
    process_cpu_load DOUBLE,
    process_cpu_time BIGINT,
    thread_cpu_time BIGINT,
    heap_max BIGINT,
    heap_usage BIGINT,
    CONSTRAINT rs_pk PRIMARY KEY (ep_id, nanotime),
    CONSTRAINT rs_ep_fk FOREIGN KEY (ep_id) REFERENCES execution_profile(rtp_id)
);

CREATE TABLE jvm_profile (
    id IDENTITY PRIMARY KEY,
    node_id BIGINT,
    nanotime BIGINT,
    process_cpu_time BIGINT,
    process_cpu_load_max DOUBLE,
    process_cpu_load_avg DOUBLE,
    process_cpu_load_min DOUBLE,
    system_cpu_load_max DOUBLE,
    system_cpu_load_avg DOUBLE,
    system_cpu_load_min DOUBLE,
    heap_max BIGINT,
    heap_usage_max DOUBLE,
    heap_usage_avg DOUBLE,
    heap_usage_min DOUBLE,
    CONSTRAINT jvmp_unique UNIQUE (node_id, nanotime)
);