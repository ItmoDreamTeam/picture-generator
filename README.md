# Picture Generator Proxy

Proxy app that allows to bypass CORS for [web app](https://github.com/KirillSmirnow/picture-generator)

### API

GET:
* /picture/{width}/{height}/\*: Random picture
* /fs: Meta data of pictures on the FS server
* /fs/{id}: Picture by id from FS server

POST:
* /fs: Upload picture onto FS server
* /vk/upload: Upload photo onto VK server by the given URL

FS server: https://github.com/fs-group/file-storage-server
