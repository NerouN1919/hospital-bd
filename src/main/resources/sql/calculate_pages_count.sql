CREATE OR REPLACE PROCEDURE calculate_pages_count(INOUT pages_count INT, IN page_size INT)
AS $$
DECLARE
    total_wards_count INT;
BEGIN
    -- Получаем общее количество палат
    SELECT COUNT(*) INTO total_wards_count FROM wards;

    -- Вычисляем количество страниц
    pages_count := CEIL(total_wards_count::FLOAT / page_size);
END;
$$ LANGUAGE plpgsql;