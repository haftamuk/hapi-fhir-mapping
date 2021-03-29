# HAPI FHIR Plain Server Skeleton

To try this project out:

## Create database tables
<p> create a database with the following information </p>

```
+---------------------------+
| Tables_in_smart_care_demo |
+---------------------------+
| CFDH_condition            |
| CFDH_patient              |
+---------------------------+
```

mysql> describe CFDH_patient;

```
+-------+--------------+------+-----+---------+----------------+
| Field | Type         | Null | Key | Default | Extra          |
+-------+--------------+------+-----+---------+----------------+
| id    | int(11)      | NO   | PRI | NULL    | auto_increment |
| age   | int(11)      | NO   |     | NULL    |                |
| sex   | varchar(255) | NO   |     | NULL    |                |
+-------+--------------+------+-----+---------+----------------+
```



mysql> describe CFDH_condition;
```
+------------------+--------------+------+-----+-------------------+-----------------------------+
| Field            | Type         | Null | Key | Default           | Extra                       |
+------------------+--------------+------+-----+-------------------+-----------------------------+
| id               | bigint(20)   | NO   | PRI | NULL              | auto_increment              |
| PatientID        | int(11)      | NO   | MUL | NULL              |                             |
| VisitDate        | datetime     | NO   |     | CURRENT_TIMESTAMP | on update CURRENT_TIMESTAMP |
| PrimaryDiagnosis | varchar(255) | NO   |     | NULL              |                             |
| VisitType        | varchar(255) | YES  |     | NULL              |                             |
+------------------+--------------+------+-----+-------------------+-----------------------------+
```



# Running the project
<ul>
  <li> Run the following command to compile the project and start a local testing server that runs it: </p> 

```
mvn jetty:run
```
</li>

<li> Point your browser to the following URL:

http://localhost:8080/Patient/1

</li>

<li> The response to the query above comes from the resource provider called [Example01_PatientResourceProvider.java](https://github.com/FirelyTeam/fhirstarters/blob/master/java/hapi-fhirstarters-simple-server/src/main/java/ca/uhn/fhir/example/Example01_PatientResourceProvider.java)
</li>

<li>
 Try adding other operations: create, update, search! You can use the [Operations Documentation](http://hapifhir.io/doc_rest_operations.html) for details on how other methods should look. You can also look at [HashMapResourceProvider](https://github.com/jamesagnew/hapi-fhir/blob/master/hapi-fhir-server/src/main/java/ca/uhn/fhir/rest/server/provider/HashMapResourceProvider.java) for an example resource provider with many operations implemented (using a simple in-memory HashMap as the storage mechanism, so it's easy to follow).
</li>
</ul>
