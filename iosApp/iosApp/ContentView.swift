import SwiftUI
import shared

struct ContentView: View {
    
    @State var script: String = ""
    @State var response :IdeResult<AnyObject> = IdeResult<AnyObject>.init(data: nil, message: nil, error: nil,state:nil)
    @State var errorMessage: String = ""

	var body: some View {
        VStack(alignment: .leading) {
                   TextField("Enter script...", text: $script).textFieldStyle(.roundedBorder)
                   Text("script: \(script)")
                   Text("error: \(errorMessage)")
                   Text("response: \(response)")


        }.padding()

        Button("Click") {
            let repository = IdeRepository()
            repository.getOutput(script: script) { data, error in
                if data != nil {
                    response.self = data!
                }
                if error != nil {
                    errorMessage.self = "\(String(describing: error))"
                }
            }
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
