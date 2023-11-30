CREATE OR REPLACE PROCEDURE update_person(_id bigint, _first_name text, _last_name text, _pather_name text, _diagnosis_name text)
LANGUAGE plpgsql
AS $$
DECLARE
    old_record people%ROWTYPE;
    new_diagnosis_id bigint;
BEGIN
    SELECT * INTO old_record FROM people WHERE id = _id;

    IF _first_name IS NULL OR _first_name = '' THEN
        _first_name := old_record.first_name;
    END IF;

    IF _last_name IS NULL OR _last_name = '' THEN
        _last_name := old_record.last_name;
    END IF;

    IF _pather_name IS NULL OR _pather_name = '' THEN
        _pather_name := old_record.pather_name;
    END IF;

    IF _diagnosis_name IS NOT NULL AND _diagnosis_name != '' THEN
        SELECT id INTO new_diagnosis_id FROM diagnosis WHERE name = _diagnosis_name;
        IF new_diagnosis_id IS NOT NULL THEN
            UPDATE people SET diagnosis_id = new_diagnosis_id WHERE id = _id;
        END IF;
    END IF;

    UPDATE people SET first_name = _first_name, last_name = _last_name, pather_name = _pather_name WHERE id = _id;
END;
$$;
