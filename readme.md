настройки url, user, password -   
[src/test/resources/test.properties]
***
для выполнения всех тестов
`mvn -Dtest=FinalTaskTest test`
***
для выполнения отдельного сценария :

+ Проверить, что список компаний фильтруется по параметру active=true
  `mvn -Dtest=TestDemoqa#testShouldFilteredCompaniesHavingIsActiveTrue test`
+ Проверить, что список компаний фильтруется по параметру active=false
  `mvn -Dtest=testShouldFilteredCompaniesHavingIsActiveFalse test`
+ Проверить создание сотрудника в несуществующей компании
  `mvn -Dtest=TestDemoqa#testShouldNotCreateEmployeeForNotExistCompany test`
+ Проверить, что неактивный сотрудник не отображается в
  списке `mvn -Dtest=TestDemoqa#testShouldNotHaveDeactivateEmployeeInCompanyList test`
+ Проверить, что у удаленной компании проставляется в БД поле
  deletedAt `mvn -Dtest=TestDemoqa#testShouldNotHaveNullDeleteAtAfterDelete test`
***
открыть аллюр-отчет :
`allure serve <path-to-allure-results>`



