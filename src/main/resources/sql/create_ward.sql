CREATE OR REPLACE PROCEDURE create_ward(
    p_ward_name VARCHAR,
    p_max_count BIGINT,
    INOUT result_type INT,
    INOUT result_message TEXT
) AS
$$
DECLARE
    v_ward_id bigint;
BEGIN
    -- Начинаем транзакцию
    BEGIN
        -- Проверяем, что вместительность больше 0
        IF p_max_count <= 0 THEN
            result_type := 0;
            result_message := 'Ошибка: Вместительность должна быть больше 0';
            RETURN;
        END IF;

        -- Добавляем запись в таблицу "wards"
        INSERT INTO wards(name, max_count) VALUES (p_ward_name, p_max_count) RETURNING id INTO v_ward_id;

        -- Проверяем, была ли успешно добавлена запись
        IF v_ward_id IS NOT NULL THEN
            result_type := 1;
            result_message := 'Палата успешно добавлена';
        ELSE
            result_type := 0;
            result_message := 'Ошибка при добавлении палаты';
        END IF;

        -- Фиксируем транзакцию
    EXCEPTION
        -- Обработка исключений
        WHEN OTHERS THEN
            -- Отменяем транзакцию в случае ошибки
            result_type := 0;
            result_message := 'Ошибка: ' || SQLERRM;
    END;
END;
$$ LANGUAGE plpgsql;