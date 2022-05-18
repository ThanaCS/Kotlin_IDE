import SwiftUI
import shared

struct ContentView: View {
    
    let repository = IdeRepository()
    @State var script: String = ""
    @State var response :IdeResult<AnyObject> = IdeResult<AnyObject>.init(data: nil, message: nil, error: nil, state:nil, errorResponse: nil)
    @State var errorMessage: String = ""

	var body: some View {
        VStack(alignment: .leading) {
                   TextField("Enter script...", text: $script).textFieldStyle(.roundedBorder)
                   Text("script: \(script)")
                   Text("response: \(response)")
                   Text("error: \(errorMessage)")


        }.padding()

        Button("Play") {
            
            repository.getResponse(script: script){ data, error in
                if let response = data {
                       self.response = response
                   }
                if error != nil {
                    self.errorMessage = "\(String(describing: error))"
                  }
            }
        
        }
	}
}

//func doRegularWork(script:String) {
//
//}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
