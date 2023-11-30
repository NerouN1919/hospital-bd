CREATE OR REPLACE FUNCTION add_person_to_ward(
    p_first_name text,
    p_last_name text,
    p_pather_name text,
    p_diagnosis_name text,
    p_ward_id bigint
)
RETURNS TABLE(result_code integer, result_message text) AS $$
DECLARE
    new_person_id bigint;
    diagnosis_need_id bigint;
    existing_diagnosis_id bigint;
BEGIN
    -- Начинаем транзакцию
    BEGIN
        -- Проверяем, существует ли палата
        IF NOT EXISTS (SELECT 1 FROM wards WHERE id = p_ward_id) THEN
            RETURN QUERY SELECT 0, 'Палата не найдена';
            RETURN;
        END IF;

        -- Ищем диагноз по названию
        SELECT id INTO diagnosis_need_id FROM diagnosis WHERE name = p_diagnosis_name;

        -- Если диагноз не найден, возвращаем ошибку
        IF diagnosis_need_id IS NULL THEN
            RETURN QUERY SELECT 0, 'Диагноз не найден';
            RETURN;
        END IF;

        -- Проверяем, есть ли в палате человек с другим диагнозом
        SELECT diagnosis_id INTO existing_diagnosis_id FROM people WHERE ward_id = p_ward_id LIMIT 1;

        IF existing_diagnosis_id IS NOT NULL AND existing_diagnosis_id != diagnosis_need_id THEN
            RETURN QUERY SELECT 0, 'В палате есть человек с другим диагнозом';
            RETURN;
        END IF;

        -- Пытаемся вставить нового пользователя, триггер выполнит проверку переполнения палаты
        INSERT INTO people (first_name, last_name, pather_name, diagnosis_id, ward_id)
        VALUES (p_first_name, p_last_name, p_pather_name, diagnosis_need_id, p_ward_id)
        RETURNING id INTO new_person_id;

    EXCEPTION
        WHEN OTHERS THEN
            -- Если произошла ошибка, PostgreSQL автоматически откатит транзакцию,
            -- и возвращаем сообщение об ошибке
            RETURN QUERY SELECT 0, 'Произошла ошибка: ' || SQLERRM;
            RETURN;
    END;

    -- Возвращаем успешный код результата и сообщение
    RETURN QUERY SELECT 1, 'Успешно добавлен в палату. Новый пользователь с ID: ' || new_person_id;
END;
$$ LANGUAGE plpgsql;
