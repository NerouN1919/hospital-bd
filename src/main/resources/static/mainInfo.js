function toggleMenu(element) {
    var menuBox = element.nextElementSibling; // Получаем следующий элемент после кнопки
    if (menuBox.style.display === 'block') {
        menuBox.style.display = "none";
    } else {
        menuBox.style.display = "block";
    }
}

