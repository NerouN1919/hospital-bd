CREATE OR REPLACE FUNCTION get_people_in_ward(ward_id_param bigint)
RETURNS TABLE (
    person_id bigint,
    first_name varchar(50),
    last_name varchar(50),
    pather_name varchar(50),
    diagnosis_name varchar(50)
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        p.id AS person_id,
        p.first_name,
        p.last_name,
        p.pather_name,
        d.name AS diagnosis_name
    FROM
        people p
    JOIN
        diagnosis d ON p.diagnosis_id = d.id
    WHERE
        p.ward_id = ward_id_param;
END;
$$ LANGUAGE plpgsql;
