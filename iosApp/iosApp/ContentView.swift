import SwiftUI
import shared

struct ContentView: View {
    
    @State var script: String = "fun main (args: Array <String> ) {      \n\n\n\n\n}"
    @State var response :IdeResult<AnyObject> = IdeResult<AnyObject>.init(data: nil, message: nil, error:nil,state:nil)
    @State var errorMessage: String = ""
    @State var isRunning :Bool = false
    
    var body: some View {
        NavigationView {
            TextEditor( text: $script)
                .padding(.leading)
                .textFieldStyle(.roundedBorder)
                .toolbar(content: {
                    ToolbarItem(placement: .navigationBarLeading) {
                        HStack {
                            Button(action: { }) {
                                Circle()
                                    .fill(.red)
                                    .frame(width: 14, height: 18)
                            }.disabled(true)
                            
                            
                            Button(action: { }) {
                                Circle()
                                    .fill(.yellow)
                                    .frame(width: 14, height: 18)
                            }.disabled(true)
                            
                            Button(action: { }) {
                                Circle()
                                    .fill(.green)
                                    .frame(width: 14, height: 18)
                            }.disabled(true)
                        }.padding()
                        
                    }
                    ToolbarItem(placement: .navigationBarTrailing) {
                        Button(action: {script = "fun main (args: Array <String> ) {      \n\n\n\n\n}" }) {
                            Image(systemName: "trash.fill")
                                .imageScale(.large)
                        }
                    }
                    
                    ToolbarItem(placement: .navigationBarTrailing) {
                        Button(action: {shareButton(script: [$script]) }) {
                            Image(systemName: "square.and.arrow.up")
                                .imageScale(.large)
                        }
                    }
                    ToolbarItem(placement: .navigationBarLeading) {
                        Button(action: {
                            let repository = IdeRepository()
                            isRunning.self = true
                            repository.getOutput(script: script) { data, error in
                                if data != nil {
                                    response.self = data!
                                    isRunning.self = false
                                }
                                if error != nil {
                                    errorMessage.self = "\(String(describing: error))"
                                    isRunning.self = false
                                }
                            }
                            
                        }) {
                            Image(systemName: "play.fill")
                                .imageScale(.large)
                        }
                    }
                    
                }
                )
        }.padding()
        
        
        VStack(alignment: .center) {
            if let output : String = response.self.data?.output {
                GeometryReader { geometry in
                    ScrollView {
                        Text("Output:\n\(String(describing: output.description).replacingOccurrences(of: "jdoodle.kt", with: ""))").padding()
                    }
                }
            }
            if(isRunning.self){
                ProgressView("Running..")
            }
            
        }.padding()
        
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

func shareButton(script:[Any]) {
    let activityController = UIActivityViewController(activityItems: script, applicationActivities: nil)
    
    UIApplication.shared.windows.first?.rootViewController!.present(activityController, animated: true, completion: nil)
}

