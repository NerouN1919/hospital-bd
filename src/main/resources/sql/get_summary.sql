CREATE OR REPLACE FUNCTION get_summary()
RETURNS TABLE(id BIGINT, first_name varchar(20), last_name varchar(20), pather_name varchar(20), diagnosis_name varchar(50), ward_name varchar(20), max_count BIGINT) AS $$
BEGIN
RETURN QUERY
SELECT people.id, people.first_name, people.last_name, people.pather_name, diagnosis.name, wards.name, wards.max_count
FROM people
         INNER JOIN diagnosis ON people.diagnosis_id = diagnosis.id
         INNER JOIN wards ON people.ward_id = wards.id;
END; $$
LANGUAGE plpgsql;