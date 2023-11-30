CREATE OR REPLACE FUNCTION get_ward_occupancy() RETURNS TABLE(ward_name VARCHAR, occupancy_rate FLOAT) AS $$
DECLARE
    ward_record wards%ROWTYPE;
    occupancy FLOAT;
BEGIN
    FOR ward_record IN SELECT * FROM wards
    LOOP
        CALL calculate_occupancy(ward_record.id, occupancy);
        ward_name := ward_record.name;
        occupancy_rate := occupancy;
        RETURN NEXT;
    END LOOP;
END; $$
LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE calculate_occupancy(IN need_id BIGINT, INOUT occupancy_rate FLOAT) AS $$
DECLARE
    total_people BIGINT;
    max_count_ward BIGINT;
BEGIN
    SELECT COUNT(*) INTO total_people FROM people WHERE people.ward_id = need_id;
    SELECT max_count INTO max_count_ward FROM wards WHERE wards.id = need_id;
    IF max_count_ward > 0 THEN
        occupancy_rate := (total_people::FLOAT / max_count_ward) * 100;
    ELSE
        occupancy_rate := 0;
    END IF;
END; $$
LANGUAGE plpgsql;