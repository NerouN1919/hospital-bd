CREATE OR REPLACE FUNCTION get_all_diagnoses()
RETURNS TABLE(name_diagnos varchar(50)) AS $$
BEGIN
    RETURN QUERY SELECT name FROM diagnosis;
END; $$
LANGUAGE plpgsql;
