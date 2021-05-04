-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: ultimosistema
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cargo`
--

DROP TABLE IF EXISTS `cargo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cargo` (
  `idCargo` int NOT NULL AUTO_INCREMENT,
  `cargo` varchar(60) COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`idCargo`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cargo`
--

LOCK TABLES `cargo` WRITE;
/*!40000 ALTER TABLE `cargo` DISABLE KEYS */;
INSERT INTO `cargo` VALUES (17,'Doctor'),(19,'Enfermera'),(20,'Asistente');
/*!40000 ALTER TABLE `cargo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `citamedica`
--

DROP TABLE IF EXISTS `citamedica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `citamedica` (
  `idCitaMedica` int NOT NULL AUTO_INCREMENT,
  `citaMedicaObservaciones` text COLLATE utf8_spanish_ci NOT NULL,
  `citaMedicaFecha` date NOT NULL,
  `citaMedicaHoraInicio` time NOT NULL,
  `citaMedicaHoraFinal` time NOT NULL,
  `citaMedicaNumeroIdentidad` varchar(15) COLLATE utf8_spanish_ci NOT NULL,
  `idUsuario` int NOT NULL,
  PRIMARY KEY (`idCitaMedica`),
  KEY `fk_cita_medica_usuario1_idx` (`idUsuario`),
  CONSTRAINT `fk_cita_medica_usuario1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `citamedica`
--

LOCK TABLES `citamedica` WRITE;
/*!40000 ALTER TABLE `citamedica` DISABLE KEYS */;
INSERT INTO `citamedica` VALUES (8,'gripe','1970-01-01','12:00:00','01:00:00','0801-2000-00733',1),(9,'Migraña','1970-01-01','10:00:00','11:00:00','0801-1989-00244',1),(10,'Dolor estomacal','2021-05-03','09:00:00','10:00:00','1709-1989-00003',1);
/*!40000 ALTER TABLE `citamedica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `consultamedica`
--

DROP TABLE IF EXISTS `consultamedica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `consultamedica` (
  `idConsultasMedicas` int NOT NULL AUTO_INCREMENT,
  `consultaMedicaFechaIngreso` date NOT NULL,
  `consultaMedicaObservaciones` text COLLATE utf8_spanish_ci NOT NULL,
  `consultaMedicaRecetasMedicas` text COLLATE utf8_spanish_ci NOT NULL,
  `consultaMedicaNumeroIdentidad` varchar(15) COLLATE utf8_spanish_ci NOT NULL,
  `idUsuario` int NOT NULL,
  PRIMARY KEY (`idConsultasMedicas`),
  KEY `fk_consulta_medica_usuario1_idx` (`idUsuario`),
  CONSTRAINT `fk_consulta_medica_usuario1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consultamedica`
--

LOCK TABLES `consultamedica` WRITE;
/*!40000 ALTER TABLE `consultamedica` DISABLE KEYS */;
INSERT INTO `consultamedica` VALUES (37,'2021-05-03','malestares estomacales','nsp','0801-2000-00733',1),(40,'2021-05-04','Dolor estomacal','Antiespasmodico','1709-1989-00003',1);
/*!40000 ALTER TABLE `consultamedica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleado`
--

DROP TABLE IF EXISTS `empleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empleado` (
  `idEmpleado` int NOT NULL AUTO_INCREMENT,
  `empleadoPrimerNombre` varchar(15) COLLATE utf8_spanish_ci NOT NULL,
  `empleadoSegundoNombre` varchar(15) COLLATE utf8_spanish_ci DEFAULT NULL,
  `empleadoPrimerApellido` varchar(15) COLLATE utf8_spanish_ci NOT NULL,
  `empleadoSegundoApellido` varchar(15) COLLATE utf8_spanish_ci DEFAULT NULL,
  `empleadoDireccion` varchar(45) COLLATE utf8_spanish_ci NOT NULL,
  `empleadoTelefonoCelular` varchar(9) COLLATE utf8_spanish_ci NOT NULL,
  `idCargo` int NOT NULL,
  `idEstado` int NOT NULL,
  PRIMARY KEY (`idEmpleado`),
  KEY `fk_empleados_cargo1_idx` (`idCargo`),
  KEY `fk_empleado_estado_empleado1_idx` (`idEstado`),
  CONSTRAINT `fk_empleado_estado_empleado1` FOREIGN KEY (`idEstado`) REFERENCES `estadoempleado` (`idEstado`),
  CONSTRAINT `fk_empleados_cargo1` FOREIGN KEY (`idCargo`) REFERENCES `cargo` (`idCargo`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleado`
--

LOCK TABLES `empleado` WRITE;
/*!40000 ALTER TABLE `empleado` DISABLE KEYS */;
INSERT INTO `empleado` VALUES (1,'Luis','Antonio','Mendoza','Rodriguez','Choluteca','8765-0098',17,1);
/*!40000 ALTER TABLE `empleado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estadoempleado`
--

DROP TABLE IF EXISTS `estadoempleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estadoempleado` (
  `idEstado` int NOT NULL AUTO_INCREMENT,
  `estado` varchar(9) COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`idEstado`),
  UNIQUE KEY `estado_UNIQUE` (`estado`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estadoempleado`
--

LOCK TABLES `estadoempleado` WRITE;
/*!40000 ALTER TABLE `estadoempleado` DISABLE KEYS */;
INSERT INTO `estadoempleado` VALUES (1,'Activo'),(2,'Inactivo');
/*!40000 ALTER TABLE `estadoempleado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historiaclinica`
--

DROP TABLE IF EXISTS `historiaclinica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historiaclinica` (
  `numeroIdentidad` varchar(15) COLLATE utf8_spanish_ci NOT NULL,
  `historiaClinicaFechaCreacion` date NOT NULL,
  `historiaClinicaCardiobasculares` varchar(8) COLLATE utf8_spanish_ci DEFAULT NULL,
  `historiaClinicaPulmonares` varchar(8) COLLATE utf8_spanish_ci DEFAULT NULL,
  `historiaClinicaDigestivo` varchar(8) COLLATE utf8_spanish_ci DEFAULT NULL,
  `historiaClinicaDiabetes` varchar(8) COLLATE utf8_spanish_ci DEFAULT NULL,
  `historiaClinicaRenales` varchar(8) COLLATE utf8_spanish_ci DEFAULT NULL,
  `historiaClinicaQuirurgicos` varchar(8) COLLATE utf8_spanish_ci DEFAULT NULL,
  `historiaClinicaAlergicos` varchar(8) COLLATE utf8_spanish_ci DEFAULT NULL,
  `historiaClinicaTransfusiones` varchar(8) COLLATE utf8_spanish_ci DEFAULT NULL,
  `historiaClinicaMedicamentos` varchar(400) COLLATE utf8_spanish_ci DEFAULT NULL,
  `historiaClinicaObservaciones` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `idUsuario` int NOT NULL,
  PRIMARY KEY (`numeroIdentidad`),
  KEY `fk_historia_clinica_usuario1_idx` (`idUsuario`),
  CONSTRAINT `fk_historia_clinica_usuario1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`),
  CONSTRAINT `fk_historiaClinica_paciente1` FOREIGN KEY (`numeroIdentidad`) REFERENCES `paciente` (`numeroIdentidad`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historiaclinica`
--

LOCK TABLES `historiaclinica` WRITE;
/*!40000 ALTER TABLE `historiaclinica` DISABLE KEYS */;
INSERT INTO `historiaclinica` VALUES ('0801-2000-00733','2021-10-10','si','no','no','no','no','no','no','si','no','ninguna',1);
/*!40000 ALTER TABLE `historiaclinica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paciente`
--

DROP TABLE IF EXISTS `paciente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paciente` (
  `numeroIdentidad` varchar(15) COLLATE utf8_spanish_ci NOT NULL,
  `pacientePrimerNombre` varchar(15) COLLATE utf8_spanish_ci NOT NULL,
  `pacienteSegundoNombre` varchar(15) COLLATE utf8_spanish_ci DEFAULT NULL,
  `pacientePrimerApellido` varchar(15) COLLATE utf8_spanish_ci NOT NULL,
  `pacienteSegundoApellido` varchar(15) COLLATE utf8_spanish_ci DEFAULT NULL,
  `pacienteAntecedentesFamiliares` varchar(100) COLLATE utf8_spanish_ci DEFAULT NULL,
  `pacienteFechaNacimiento` date NOT NULL,
  `pacienteTipoSangre` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `pacienteDireccion` varchar(75) COLLATE utf8_spanish_ci NOT NULL,
  `pacienteTelefonoCelular` varchar(9) COLLATE utf8_spanish_ci NOT NULL,
  `pacientePeso` decimal(7,2) NOT NULL,
  `pacienteEstatura` decimal(5,2) NOT NULL,
  `pacienteCiudadProcedencia` varchar(65) COLLATE utf8_spanish_ci NOT NULL,
  `pacienteEmail` varchar(45) COLLATE utf8_spanish_ci DEFAULT NULL,
  `idSexo` int NOT NULL,
  PRIMARY KEY (`numeroIdentidad`),
  KEY `fk_pacientes_sexo1_idx` (`idSexo`),
  CONSTRAINT `fk_pacientes_sexo1` FOREIGN KEY (`idSexo`) REFERENCES `sexo` (`idSexo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paciente`
--

LOCK TABLES `paciente` WRITE;
/*!40000 ALTER TABLE `paciente` DISABLE KEYS */;
INSERT INTO `paciente` VALUES ('0615-2000-00766','Ana','Yamileth','Garcia','Cruz','Diabetis','2000-05-09','O+','San Lorenzo, Valle','3266-0547',32.20,1.68,'Nacaome','ana@gmail.com',1),('0801-1989-00244','Carlos','Antonio','Espinoza','Corrales','ninguno','1970-01-01','O+','El Corpus','8970-1212',60.10,1.88,'Tegucigalpa','carl@gmail.com',2),('0801-2000-00733','Katia','Lisbeth','Aguilar','Garcia','ninguno','1970-01-01','A+','El Corpus, Choluteca','9800-7645',22.50,1.66,'Tegucigalpa','kat@g,ail.com',1),('1709-1989-00003','Kristel','Esther','Aguilar','Garcia','diabetes','1989-05-06','A+','San Marcos de Colon','8906-0098',34.90,1.78,'San Lorenzo','kristel@gmail.com',1);
/*!40000 ALTER TABLE `paciente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sexo`
--

DROP TABLE IF EXISTS `sexo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sexo` (
  `idSexo` int NOT NULL AUTO_INCREMENT,
  `sexo` varchar(9) COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`idSexo`),
  UNIQUE KEY `sexo_UNIQUE` (`sexo`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sexo`
--

LOCK TABLES `sexo` WRITE;
/*!40000 ALTER TABLE `sexo` DISABLE KEYS */;
INSERT INTO `sexo` VALUES (1,'femenino'),(2,'masculino');
/*!40000 ALTER TABLE `sexo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `idUsuario` int NOT NULL AUTO_INCREMENT,
  `usuarioNombreUsuario` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `usuarioPassword` varchar(9) COLLATE utf8_spanish_ci NOT NULL,
  `usuarioFechaCreacion` date NOT NULL,
  `usuarioFechaBaja` date DEFAULT NULL,
  `idEstado` int NOT NULL,
  `idEmpleado` int NOT NULL,
  PRIMARY KEY (`idUsuario`),
  KEY `fk_usuario_empleados1_idx` (`idEmpleado`),
  KEY `fk_usuario_estado_empleado1_idx` (`idEstado`),
  CONSTRAINT `fk_usuario_empleados1` FOREIGN KEY (`idEmpleado`) REFERENCES `empleado` (`idEmpleado`),
  CONSTRAINT `fk_usuario_estado_empleado1` FOREIGN KEY (`idEstado`) REFERENCES `estadoempleado` (`idEstado`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'luisiAn','1234','2020-10-10','2021-10-10',1,1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-04 23:30:51
