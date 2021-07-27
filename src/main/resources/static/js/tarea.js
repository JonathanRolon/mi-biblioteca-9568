window.onload = function(){

    function borrar(event){
        document.getElementById("_idRespuestaTarea").value = "";
    }

    let oButtonLimpiar = $('#_idButtonLimpiarRespuesta');
    oButtonLimpiar.click(borrar);

};