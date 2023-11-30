CREATE OR REPLACE FUNCTION remove_from_ward_on_diagnosis_change() RETURNS TRIGGER AS $$
BEGIN
    IF NEW.diagnosis_id <> OLD.diagnosis_id THEN
        NEW.ward_id := NULL;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_diagnosis_change
BEFORE UPDATE OF diagnosis_id ON people
FOR EACH ROW
EXECUTE PROCEDURE remove_from_ward_on_diagnosis_change();
