import Combine

// inspiration: https://www.wwt.com/article/creating-your-own-custom-combine-operator

extension Publishers {

    // A publisher that attempts to recreate its subscription to a failed upstream publisher.
    public struct RetryWithRecovery<Upstream: Publisher, RecoveryPublisher: Publisher>: Publisher {
        
        public typealias Output = Upstream.Output
        
        public typealias Failure = Upstream.Failure
        
        let upstream: Upstream
        let failureShouldRetry: (Failure) -> Bool
        let recoveryPublisher: RecoveryPublisher
        
        public init(upstream: Upstream, failureShouldRetry: @escaping (Failure) -> Bool, recoveryPublisher: RecoveryPublisher) {
            self.upstream = upstream
            self.failureShouldRetry = failureShouldRetry
            self.recoveryPublisher = recoveryPublisher
        }
        
        public func receive<S: Subscriber>(subscriber: S) where Upstream.Failure == S.Failure, Upstream.Output == S.Input {
            self.upstream
                .catch { e -> AnyPublisher<Output, Failure> in
                    guard failureShouldRetry(e) else {
                        return Fail<Output, Failure>(error: e)
                            .eraseToAnyPublisher()
                    }
                    
                    return recoveryPublisher
                        .catch { _ in Fail(error: e) }
                        .flatMap { _ in upstream }
                        .eraseToAnyPublisher()
                }
                .subscribe(subscriber)
        }
    }
    
    // A publisher that attempts to recreate its subscription to a failed upstream publisher.
    public struct RetryIf<Upstream: Publisher>: Publisher {
        
        public typealias Output = Upstream.Output
        
        public typealias Failure = Upstream.Failure
        
        let upstream: Upstream
        let retries: Int
        let failureShouldRetry: (Failure) -> Bool
        
        public init(upstream: Upstream, retries: Int, failureShouldRetry: @escaping (Failure) -> Bool) {
            self.upstream = upstream
            self.retries = retries
            self.failureShouldRetry = failureShouldRetry
        }
        
        public func receive<S: Subscriber>(subscriber: S) where Upstream.Failure == S.Failure, Upstream.Output == S.Input {
            self.upstream
                .catch { e -> AnyPublisher<Output, Failure> in
                    guard failureShouldRetry(e), retries > 0 else {
                        return Fail<Output, Failure>(error: e)
                            .eraseToAnyPublisher()
                    }
                    
                    return upstream
                        .retryIf(retries: retries - 1, failureShouldRetry: failureShouldRetry)
                        .eraseToAnyPublisher()
                }
                .subscribe(subscriber)
        }
    }
}

extension Publisher {
    
    /// Description
    /// - Parameters:
    ///   - recoveryPublisher: a publisher that will be subscribed to on upstream error
    ///   - failureShouldRetry: Decide whether publisher should retry after a failure
    public func retryWithRecovery<RecoveryPublisher: Publisher>(
        recoveryPublisher: RecoveryPublisher,
        failureShouldRetry: @escaping (Failure) -> Bool
    ) -> Publishers.RetryWithRecovery<Self, RecoveryPublisher> {
        .init(upstream: self,
              failureShouldRetry: failureShouldRetry,
              recoveryPublisher: recoveryPublisher
        )
    }
    
    /// Description:
    /// Attempts to recreate a failed subscription with the upstream publisher up to the number of times you specify, when `failureShouldRetry` returns `true`
    /// - Parameters:
    ///   - retries: number of retries
    ///   - failureShouldRetry: condition check before each retry
    public func retryIf(
        retries: Int,
        failureShouldRetry: @escaping (Failure) -> Bool
    ) -> Publishers.RetryIf<Self> {
        .init(
            upstream: self,
            retries: retries,
            failureShouldRetry: failureShouldRetry
        )
    }
}
