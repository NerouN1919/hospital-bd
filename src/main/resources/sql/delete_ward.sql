CREATE OR REPLACE PROCEDURE delete_ward(_id bigint)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE people SET ward_id = NULL WHERE ward_id = _id;

    DELETE FROM wards WHERE id = _id;
END;
$$;
