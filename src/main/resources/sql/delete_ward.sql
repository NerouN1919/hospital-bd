CREATE OR REPLACE PROCEDURE delete_ward(_id bigint)
LANGUAGE plpgsql
AS $$
BEGIN
    -- Удаляем все ссылки на палату в таблице people
    UPDATE people SET ward_id = NULL WHERE ward_id = _id;

    -- Удаляем палату
    DELETE FROM wards WHERE id = _id;
END;
$$;
