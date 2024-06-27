function resetSelect(select, leyenda) {
    for (let j = select.options.length - 1; j >= 0; j--)
        select.remove(j);

    let option = document.createElement("option");
    option.text = leyenda;
    option.selected = true;
    select.appendChild(option);
}

function agregarOpcion(select, array) {
    let option = document.createElement("option");
    option.value = array[0];
    option.text = array[1];
    select.appendChild(option);
}