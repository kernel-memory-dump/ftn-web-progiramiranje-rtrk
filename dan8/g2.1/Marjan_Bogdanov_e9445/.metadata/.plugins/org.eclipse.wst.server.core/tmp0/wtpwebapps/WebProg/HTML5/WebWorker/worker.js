console.log("Prva naredba u workeru");
self.postMessage("Šaljem poruku u glavnu nit, izvan onmessage hendlera.");

self.onmessage = function (oEvent) {
  console.log("Worker primio: " + oEvent.data);
  self.postMessage("Hi " + oEvent.data);
};