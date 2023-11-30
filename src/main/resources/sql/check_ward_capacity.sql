CREATE OR REPLACE FUNCTION check_ward_capacity()
RETURNS TRIGGER AS $$
DECLARE
    max_count_var INTEGER;
    current_count INTEGER;
BEGIN
    -- Получаем максимальное количество пациентов для данной палаты
    SELECT max_count INTO max_count_var FROM wards WHERE id = NEW.ward_id;

    -- Получаем текущее количество пациентов в палате
    SELECT COUNT(*) INTO current_count FROM people WHERE ward_id = NEW.ward_id;

    -- Проверяем, не превышено ли максимальное количество пациентов
    IF current_count >= max_count_var THEN
        -- Если палата переполнена, вызываем ошибку
        RAISE EXCEPTION 'Переполненная палата. Выберите другую палату.';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_ward_capacity_trigger
BEFORE INSERT ON people
FOR EACH ROW
EXECUTE FUNCTION check_ward_capacity();