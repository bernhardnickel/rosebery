CREATE TABLE node (
    id IDENTITY PRIMARY KEY,
    node_id VARCHAR(50),
    node_purpose VARCHAR(50),
    CONSTRAINT node_unique_ct UNIQUE (node_id, node_purpose)
)

CREATE TABLE runtime_performance (
    node_id BIGINT,
    seq VARCHAR(100),
    starttime BIGINT,
    endtime BIGINT,
    duration BIGINT,
    result VARCHAR(15),
    CONSTRAINT rt_pk PRIMARY KEY (node_id, starttime, endtime),
    CONSTRAINT rt_node_fk FOREIGN KEY (node_id) REFERENCES node(id)
)