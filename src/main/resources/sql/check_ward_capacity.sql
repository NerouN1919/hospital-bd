CREATE OR REPLACE FUNCTION check_ward_capacity()
RETURNS TRIGGER AS $$
DECLARE
    max_count_var INTEGER;
    current_count INTEGER;
BEGIN
    SELECT max_count INTO max_count_var FROM wards WHERE id = NEW.ward_id;

    SELECT COUNT(*) INTO current_count FROM people WHERE ward_id = NEW.ward_id;

    IF current_count >= max_count_var THEN
        RAISE EXCEPTION 'Переполненная палата. Выберите другую палату.';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_ward_capacity_trigger
BEFORE INSERT ON people
FOR EACH ROW
EXECUTE FUNCTION check_ward_capacity();