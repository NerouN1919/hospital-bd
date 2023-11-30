CREATE OR REPLACE FUNCTION get_page_data(page_size INT, page_number INT)
RETURNS TABLE (
    ward_id bigint,
    fill_count bigint,
    max_count bigint,
    ward_name character varying
) AS $$
DECLARE
    offset_val INT;
BEGIN
    -- Вычисляем смещение для определенной страницы
    offset_val := (page_number - 1) * page_size;

    -- Используем оконные функции для нумерации строк и выборки необходимых данных
    RETURN QUERY
    WITH ranked_people AS (
        SELECT
            p.ward_id,
            ROW_NUMBER() OVER (PARTITION BY p.ward_id ORDER BY p.id) AS row_num
        FROM
            people p
    ),
    counted_wards AS (
        SELECT
            w.id AS ward_id,
            w.name AS ward_name,
            w.max_count,
            COUNT(rp.row_num) AS fill_count
        FROM
            wards w
            LEFT JOIN ranked_people rp ON w.id = rp.ward_id AND rp.row_num <= page_size
        GROUP BY
            w.id, w.name, w.max_count
    )
    SELECT
        cw.ward_id,
        cw.fill_count,
        cw.max_count,
        cw.ward_name
    FROM
        counted_wards cw
    ORDER BY
        cw.ward_id
    LIMIT
        page_size
    OFFSET
        offset_val;

END;
$$ LANGUAGE plpgsql;
