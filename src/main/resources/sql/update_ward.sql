CREATE OR REPLACE PROCEDURE update_ward(_id bigint, _name text, _max_count bigint, INOUT result_code int, INOUT result_message text)
LANGUAGE plpgsql
AS $$
DECLARE
    current_count bigint;
BEGIN
    SELECT COUNT(*) INTO current_count FROM people WHERE ward_id = _id;

    IF _max_count < 1 THEN
        result_code := 0;
        result_message := 'Ошибка: вместимость должна быть больше 1';
        RETURN;
    ELSIF _max_count < current_count THEN
        result_code := 0;
        result_message := 'Ошибка: вместимость не может быть меньше текущего количества пациентов';
        RETURN;
    END IF;

    BEGIN
        UPDATE wards SET name = _name, max_count = _max_count WHERE id = _id;
        result_code := 1;
        result_message := 'Успешно: данные палаты обновлены';
    EXCEPTION WHEN OTHERS THEN
        ROLLBACK;
        result_code := 0;
        result_message := 'Ошибка: ' || SQLERRM;
        RETURN;
    END;
END;
$$;
