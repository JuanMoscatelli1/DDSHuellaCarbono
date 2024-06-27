function mostrarFormulario(opcion) {
    const array = ["rbtn_veh_particular", "rbtn_serv_contratado", "rbtn_transp_publico", "rbtn_bici_pie"]
        .map(string => document.getElementById(string));
    array.forEach(elem => elem.style.display = "none");

    for(let i = 0; i < array.length; i++)
        if(i.toString() === opcion)
           array[i].style.display = "block";
}

function selectMiembros() {
    let organizacion_id = document.getElementById("mis_organizaciones").value;
    let selects = document.getElementsByClassName("form-miembros");

    for(let i = 0; i < selects.length; i++)
        resetSelect(selects[i], "Elija a su compañero de viaje");

    miembros[organizacion_id].forEach(m => {
        for(let i = 0; i < selects.length; i++) {
            agregarOpcion(selects[i], m);
        }
    });
}

function selectTransportePublico() {
    let tipo = document.getElementById("tipo_transp_publico").value;
    let selectTransporte = document.getElementById("transp_publico_lineas");
    resetSelect(selectTransporte, "Línea de viaje");
    transportes[tipo].forEach(t => agregarOpcion(selectTransporte, t));
}

function selectParada() {
    let id = document.getElementById("transp_publico_lineas").value;
    let selectParada = document.getElementsByClassName("form-paradas");
    resetSelect(selectParada[0], "Desde...");
    resetSelect(selectParada[1], "Hasta...");
    paradas[id].forEach(p => {
        for(let i = 0; i < selectParada.length; i++)
            agregarOpcion(selectParada[i], p);
    });
}