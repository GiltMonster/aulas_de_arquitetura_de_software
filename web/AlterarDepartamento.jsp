<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>
   
    </head>
    <body>
        <div class="container">
            <form method="POST" action="Controle">
                <input type="hidden" name="flag" value="buscarDepartamentos">
                <div class="m-1 row">
                    <div>
                    <label for="idDep" class="form-label">Id do departamento:</label>
                    <input id="idDep" class="form-control" type="text" size="3" maxlength="3" name="idDep">
                        <input class="btn btn-primary mt-3" type="submit" value="Buscar">
                    </div>
                </div>
            </form> 
                       
        </div>
    </body>
</html>
