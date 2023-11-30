CREATE OR REPLACE PROCEDURE create_diagnosis(diagnosis_name text, INOUT result_code bigint, INOUT result_message text)
LANGUAGE plpgsql
AS $$
BEGIN
    IF EXISTS (SELECT 1 FROM diagnosis WHERE name = diagnosis_name) THEN
        result_code := 0;
        result_message := 'Диагноз уже существует';
    ELSE
        INSERT INTO diagnosis (name) VALUES (diagnosis_name);
        result_code := 1;
        result_message := 'Диагноз успешно создан';
    END IF;
END; $$
