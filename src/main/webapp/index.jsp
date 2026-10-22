<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Control Gimnasio</title>
        <link rel="stylesheet" type="text/css" href="styles.css"/>
    </head>
    <body>
        <h1>Control Gimnasio</h1>
        <section>
            <h2>Agregar membresia</h2>
            <form action="SvMembresia" method="POST">
                <center>
                    <c:if test="${not empty error}">
                        <p style="color: red;">${error}</p>
                    </c:if>
                </center>
                <div>
                    <label>Nombre de la persona</label>
                    <input name="nombrePersona" type="text" maxlength="100" required />
                </div>
                <div>
                    <label>Tipo de plan</label>
                    <input name="tipoPlan" type="text" maxlength="50" required />
                </div>
                <div>
                    <label>Costo mensual</label>
                    <input name="costoMensual" type="number" min="0" required />
                </div>
                <div>
                    <label>Fecha de registro</label>
                    <input name="fechaRegistro" type="date" required />
                </div>
                <div>
                    <label>Fecha de finalizacion</label>
                    <input name="fechaFin" type="date" required />
                </div>
                <div>
                    <label>Foto</label>
                    <input name="foto" type="text" required />
                </div>
                <button type="submit">Guardar</button>
            </form>
        </section>

        <section>
            <h2>Membresias registradas</h2>
            <form method="GET" action="SvMembresia">
                <button type="submit">Refrescar</button>
            </form>
            <c:if test="${not empty lista}">
                <div class="membresias-container">
                    <c:forEach var="m" items="${lista}">
                        <div class="membresia">
                            <p>
                                <strong><c:out value="${m.nombrePersona}"/></strong>
                                <br/>
                                <strong><c:out value="${m.tipoPlan}"/></strong>
                                <br/>
                                <strong>Costo mensual:</strong> $${m.costoMensual}
                                <br/>
                                <strong>Registro:</strong> ${m.fechaRegistro}
                                <br/>
                                <strong>Finaliza:</strong> ${m.fechaFin}
                                <br/>
                                <strong>Foto:</strong>
                                <br/>
                                <img src="${m.foto}" alt="${m.nombrePersona}" />
                            </p>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
        </section>
    </body>
</html>
