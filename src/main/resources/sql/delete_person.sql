CREATE OR REPLACE PROCEDURE delete_person(p_id BIGINT)
LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM people WHERE id = p_id;
END;
$$;
