// chat - an extremely basic, but straightforward mediator design pattern example

class User {
  constructor(name) {
    this.name = name
    this.chat = null
  }

  disconnect() {
    this.chat.disconnectUser(this)
  }
  sendMessage(message, receiver) {
    this.chat.sendMessage(message, this, receiver)
  }
  receiveMessage(message, sender) {
    console.log(`${sender.name} to ${this.name}: ${message}`)
  }
}

class Chat {
  constructor() {
    this.users = {}
  }

  joinUser(user) {
    this.users[user.name] = user
    user.chat = this
  }

  disconnectUser(user) {
    delete this.users[user.name]
    user.chat = null
    console.log(`${user.name} left...`)
  }

  sendMessage(message, sender, receiver) {
    if (receiver && this.users[receiver.name]) {
      receiver.receiveMessage(message, sender)
    } else {
      for (const key in this.users) {
        if (this.users[key] === sender) continue
        this.users[key].receiveMessage(message, sender)
      }
    }
  }
}

const letsChattt = (() => {
  const Oleksii = new User('Oleksii')
  const Katarzyna = new User('Katarzyna')
  const Danylo = new User('Danylo')
  const Andrii = new User('Andrii')

  const chat = new Chat()
  chat.joinUser(Oleksii)
  chat.joinUser(Katarzyna)
  chat.joinUser(Danylo)
  chat.joinUser(Andrii)

  Katarzyna.sendMessage('Hello, the deadlines are coming to an end, where are the projects?')
  Danylo.sendMessage('Working on it!', Katarzyna)
  Oleksii.sendMessage('Ooops', Katarzyna)
  Oleksii.disconnect()
  Andrii.disconnect()
})()