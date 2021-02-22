import SwiftUI

struct MSPRequirementsView: View {

    @EnvironmentObject var requiremenetsState: RequirementsState

    @Environment(\.textStyleTextM) var textStyle
    @Environment(\.grayScale) var grayScale

    var body: some View {
        ScrollView {
            descriptionView()
            requirementsList()
        }
        .navigationBarTitle("MSP Terms of use")
        .navigationBarTitleDisplayMode(.inline)
    }

    func descriptionView() -> some View {
        VStack(alignment: .leading, spacing: 16) {
            [
                Text("You can’t use particular")
                    .fontWeight(.bold)
                ,
                Text("mobility service provider (MSP) without agreeing to their prodvider terms and conditions.")
            ]
            .joined(with: .space())

            [
                Text("You will still be able to see")
                    .fontWeight(.bold)
                ,
                Text("all the MSPs on the map or in route serach results, even withoug agreeing to the terrms.")
            ]
            .joined(with: .space())
        }
        .textStyle(textStyle)
        .foregroundColor(grayScale.gray600.color)
        .padding()
    }

    func requirementsList() -> some View {
//        ForEach(requiremenetsState.requirementStatus.requirements, id: \.self) {
//
//        }
        RequirementListItem(
            checked: .constant(false),
            title: "I agree to MSP ",
            link: ("Terms of service", {})
        )
    }
}

struct MSPRequirementsView_Previews: PreviewProvider {

    static var previews: some View {
        MSPRequirementsView()
    }
}
