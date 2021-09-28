---- Group Hosts By Hardware Info ----
-- Group hosts by CPU number and sort by descending memory size
SELECT cpu_number, id AS host_id, total_mem FROM host_info
ORDER BY cpu_number, total_mem DESC;

---- Average Memory Usage ----
-- Function to round timestamp to nearest 5 minute interval
CREATE OR REPLACE FUNCTION round5(ts timestamp) RETURNS timestamp AS
$$
BEGIN
    RETURN date_trunc('hour', ts) + date_part('minute', ts):: int / 5 * interval '5 min';
END;
$$
LANGUAGE PLPGSQL;

-- Display average percentage of memory usage over every 5 minute interval
-- Sorted by time so latest data is always at the top
SELECT id, hostname, round5(host_usage.timestamp) AS time,
       AVG(100*(total_mem - host_usage.memory_free)/total_mem) AS avg_used_mem_percentage
    FROM host_info
        JOIN host_usage
            ON id=host_id
    GROUP BY time, id
    ORDER BY id, time DESC;

---- Detect Host Failure ----
-- Fewer than 3 entries in a 5 minute interval counts as a server failure
-- Stores timestamps of all server failure events
SELECT host_id, round5(timestamp) AS time_of_failure, count(*) AS number_of_entries FROM host_usage
-- Ensures current time interval doesn't count as failure
    WHERE round5(timestamp) < current_timestamp - (5*interval '1 minute')
    GROUP BY time_of_failure, host_id
        HAVING COUNT(*) < 3
    ORDER BY time_of_failure desc
