// builder design pattern example - building an airport

class Airport {
  constructor() {
    this.runways = null
    this.terminals = null
    this.gates = null
    this.airСarriers = null
  }

  buildRunways(runways) {
    this.runways = runways
  }

  buildTerminals(terminals) {
    this.terminals = terminals
  }

  buildGates(gates) {
    this.gates = gates
  }

  registerAirCarriers(airCarriers) {
    this.airСarriers = airCarriers
  }
}

class AirportBuilder {
  constructor() {
    this.airport = new Airport()
  }

  buildRunways(runways) {
    this.airport.buildRunways(runways)
    return this
  }

  buildTerminals(terminals) {
    this.airport.buildTerminals(terminals);
    return this
  }

  buildGates(gates) {
    this.airport.buildGates(gates);
    return this
  }

  registerAirCarriers(carriers) {
    this.airport.registerAirCarriers(carriers)
    return this
  }

  getAirport() {
    const airport = this.airport
    this.reset()
    console.log(`An airport with ${airport.runways} runways, ${airport.terminals} terminals and ${airport.gates} gates has been built. Registered carriers are: ${airport.airСarriers.join(', ')}`)
    return airport
  }

  reset() {
    this.airport = new Airport()
  }
}

const letsBuild = (() => {
  const builder = new AirportBuilder()
  builder
    .buildRunways(4)
    .buildTerminals(2)
    .buildGates(10)
    .registerAirCarriers(['RyanAir', 'WizzAir', 'LOT'])
    .getAirport()

  builder
    .buildRunways(15)
    .buildTerminals(4)
    .buildGates(100)
    .registerAirCarriers(['LOT', 'Turkish Airlines', 'KLM', 'UIA', 'BUZZ', 'Wizz Air', 'Ryan Air'])
    .getAirport()
})()