# Home Work 4 - Инфраструктурные паттерны
Базовые сущности Кubernetes: Service, Ingress, Configmap, Secrets
<br>Шаблонизация манифестов

### Цель: создать простейший RESTful CRUD.

### Описание/Пошаговая инструкция выполнения домашнего задания

Сделать простейший RESTful CRUD по созданию, удалению, просмотру и обновлению пользователей.
<br>Пример API - [https://app.swaggerhub.com/apis/otus55/users/1.0.0](https://app.swaggerhub.com/apis/otus55/users/1.0.0)
<br>Добавить базу данных для приложения.
<br>Конфигурация приложения должна хранится в Configmaps.
<br>Доступы к БД должны храниться в Secrets.
<br>Первоначальные миграции должны быть оформлены в качестве Job-ы, если это требуется.
<br>Ingress-ы должны также вести на url arch.homework/ (как и в прошлом задании)
<br>На выходе должны быть предоставлена:
1. ссылка на директорию в github, где находится директория с манифестами кубернетеса;
2. инструкция по запуску приложения;
<ul>
<li>команда установки БД из helm, вместе с файлом values.yaml;</li>
<li>команда применения первоначальных миграций</li>
<li>команда kubectl apply -f, которая запускает в правильном порядке манифесты кубернетеса;</li>
</ul>
3. Postman коллекция, в которой будут представлены примеры запросов к сервису на создание, получение, 
изменение и удаление пользователя.

Важно: в postman коллекции использовать базовый url - arch.homework.

Задание со звездочкой:
+5 балла за шаблонизацию приложения в helm чартах.

### Инструкция запуска манифестов

1. Запуск кластера minikube в Docker на Windows
```
minikube start driver=docker
```
2. Добавление ingress controller в minikube (включение minikube addon "ingress")
```
minikube addons enable ingress
```
3. Создание ресурсов: namespace, configmap, secret, pv и pvc
```
kubectl apply -f k8s/manifest/namespace -f k8s/manifest/resource --recursive
```
4. Установка БД
```
# добавляем репозиторий bitnami с чартами, если еще не добавлен
helm repo add bitnami https://charts.bitnami.com/bitnami

# установка PostgreSQL с помощью Helm Chart и параметров в values.yaml
helm install postgresql -n dev -f k8s/manifest/db/values.yaml bitnami/postgresql
```
5. Развертывание микросервиса. Создание ресурсов: service, deployment, ingress
```
kubectl apply -f k8s/manifest
```
6. Открытие туннеля minikube
```
minikube tunnel
```
7. Проверка
```
curl http://arch.homework/health
```
8. Swagger

[http://arch.homework/swagger-ui/index.html](http://arch.homework/swagger-ui/index.html)

### Инструкция запуска helm (задание с *)

1. Проверка на ошибки
```
helm lint k8s/helm -f k8s/helm/values-dev.yaml
```
2. Загрузка зависимостей
```
helm dependency build k8s/helm
```
3. Установка HELM с д/з №4, включая БД PostgreSQL
```
helm install home-work-4 --namespace=dev --create-namespace -f k8s/helm/values-dev.yaml k8s/helm/
```
4. Проверка
```
curl http://arch.homework/health
```
5. Swagger

[http://arch.homework/swagger-ui/index.html](http://arch.homework/swagger-ui/index.html)