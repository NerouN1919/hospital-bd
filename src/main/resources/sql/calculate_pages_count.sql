CREATE OR REPLACE PROCEDURE calculate_pages_count(INOUT pages_count INT, IN page_size INT)
AS $$
DECLARE
    total_wards_count INT;
BEGIN
    SELECT COUNT(*) INTO total_wards_count FROM wards;

    pages_count := CEIL(total_wards_count::FLOAT / page_size);
END;
$$ LANGUAGE plpgsql;