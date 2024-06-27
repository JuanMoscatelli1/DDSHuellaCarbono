function selectSectores() {
    let organizacion_id = document.getElementById("organizaciones").value;
    let sectores_select = document.getElementById("sectores");
    resetSelect(sectores_select, "Sector");
    sectores[organizacion_id].forEach(s => agregarOpcion(sectores_select, s));
}