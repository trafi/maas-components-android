public extension Text {

    /**
     Shortcut for ** • ** with whitetspaces symbol.
     - parameters:
        - spacedOut: Defines does dot symbol should by between whitespaces.
     */
    static func dot(spacedOut: Bool = false) -> Text {
        let dot = " • "
        return .init(spacedOut ? dot.trimmingCharacters(in: .whitespaces) : dot)
    }

    /**
     Shortcut for ** " " ** space seperator.
     */
    static func space() -> Text {
        let space = " "
        return .init(space)
    }
}

public extension Array where Element == Text {

    /** Joins array of **[Text]** object with passed separator. */
    func joined(with text: Text) -> Text {
        enumerated().reduce(Text("")) {
            if $1.offset == 0 { return $1.element }
            return $0 + text + $1.element
        }
    }
}
