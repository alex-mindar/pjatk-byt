// calculator, chain of responsibility design pattern example

const init = (number) => {
  let res = number
  return {
    sum: function(number) {
      res += number
      return this
    },
    substract: function(number) {
      res -= number
      return this
    },
    divide: function(number) {
      res /= number
      return this
    },
    multiply: function(number) {
      res *= number
      return this
    },
    sqrt: function() {
      res = Math.sqrt(res)
      return this
    },
    result: () => {
      return res
    }
  }
}

console.log(init(5).sum(10).substract(7).divide(4).sqrt().result())