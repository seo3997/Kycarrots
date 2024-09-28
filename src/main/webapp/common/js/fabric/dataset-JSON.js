export default class DatasetJSON {
  constructor({ images, 
                annotations, 
                cropLabels, 
                info = { name: "", description: "", date_created: "" },
                metadata = { metaclass: "", subclass: "", area: "", device: "",weather: "", illuminance: "", light: "", outline: "", wordorientation: "", wordsize:"",wordfont: "",wordcolor: "", wordconnection: "" } 
              }) {
    this.images = images || []
    this.annotations = annotations || []
    this.cropLabels = cropLabels || []
    this.info = info
    this.metadata = metadata
  }

  merge(other) {
    this.images = this.images.concat(other.images)
    this.annotations = this.annotations.concat(other.annotations)
    this.cropLabels = this.cropLabels.concat(other.cropLabels)
  }
}
